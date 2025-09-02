package be.lionelh.whist.score.backend.data;

import be.lionelh.whist.score.backend.data.dao.*;
import be.lionelh.whist.score.backend.data.domain.*;
import be.lionelh.whist.score.backend.data.domain.pk.PlayerDrawPK;
import be.lionelh.whist.score.backend.data.domain.pk.ResultRolePK;
import be.lionelh.whist.score.backend.rest.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("dataService")
public class DataService {

    private PlayerDAO playerDao;
    private RoleDAO roleDao;
    private ContractDAO contractDao;
    private ResultDAO resultDao;
    private ResultRoleDAO resultRoleDao;
    private EventDAO eventDao;
    private DrawDAO drawDao;
    private PlayerDrawDAO playerDrawDao;

    @Autowired
    private void setPlayerDao(PlayerDAO inPlayerDao) {
        this.playerDao = inPlayerDao;
    }

    @Autowired
    private void setRoleDao(RoleDAO inRoleDao) {
        this.roleDao = inRoleDao;
    }

    @Autowired
    public void setContractDao(ContractDAO contractDao) {
        this.contractDao = contractDao;
    }

    @Autowired
    public void setResultDao(ResultDAO resultDao) {
        this.resultDao = resultDao;
    }

    @Autowired
    public void setResultRoleDao(ResultRoleDAO resultRoleDao) {
        this.resultRoleDao = resultRoleDao;
    }

    @Autowired
    public void setEventDao(EventDAO eventDao) {
        this.eventDao = eventDao;
    }

    @Autowired
    public void setDrawDao(DrawDAO drawDao) {
        this.drawDao = drawDao;
    }

    @Autowired
    public void setPlayerDrawDao(PlayerDrawDAO playerDrawDao) {
        this.playerDrawDao = playerDrawDao;
    }

    /*
     ***************************************
     * Player methods *
     ***************************************
     */
    public List<Player> findAllPlayersSorted() {
        return this.playerDao.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Player createOrUpdatePlayer(Player inPlayer) {
        return this.playerDao.saveAndFlush(inPlayer);
    }

    public Player findPlayerById(long inId) {
        return this.playerDao.findById(inId).orElse(null);
    }

    public boolean playerNameExists(String inName) {
        return this.playerDao.existsPlayerByName(inName);
    }

    /*
     ***************************************
     * Role methods *
     ***************************************
     */
    public List<Role> findAllRolesSorted() {
        return this.roleDao.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Role createOrUpdateRole(Role inRole) {
        return this.roleDao.saveAndFlush(inRole);
    }

    public Role findRoleById(Long inId) {
        return this.roleDao.findById(inId).orElse(null);
    }

    public boolean roleNameExists(String inName) {
        return this.roleDao.existsRoleByName(inName);
    }

    /*
     ***************************************
     * Contract methods *
     ***************************************
     */
    public Contract createOrUpdateContract(Contract inContract) {
        return this.contractDao.saveAndFlush(inContract);
    }

    public Contract findContractById(Long inId) {
        return this.contractDao.findById(inId).orElse(null);
    }

    public List<Contract> findAllContractsSorted() {
        return this.contractDao.findAll(Sort.by(Sort.Order.asc("numberOfPlayers"), Sort.Order.asc("name")));
    }

    public boolean existsContractByNameAndNumberOfPlayers(String inName, short inNumberOfPlayers) {
        return this.contractDao.existsContractByNameAndNumberOfPlayers(inName, inNumberOfPlayers);
    }

    public List<Contract> findContractsByNumberOfPlayers(Short inNumberOfPlayers) {
        return this.contractDao.findContractsByNumberOfPlayers(inNumberOfPlayers);
    }

    /*
     ***************************************
     * Result methods *
     ***************************************
     */
    public List<ResultVO> findAllResultsSorted() {
        List<Result> lr = this.resultDao.findAll(
                Sort.by(Sort.Order.asc("numberOfPlayers"), Sort.Order.asc("contract.name"), Sort.Order.asc("name")));
        List<ResultVO> returnValue = new ArrayList<>();
        lr.forEach((r) -> {
            ResultVO rv = new ResultVO(r.getName(), r.getContract());
            rv.setId(r.getId());
            this.resultRoleDao.findByResultId(r.getId())
                    .forEach((rr) -> rv.getRoleScores().add(new RoleScoreVO(rr.getRole().getName(), rr.getScore())));
            returnValue.add(rv);
        });
        return returnValue;
    }

    public ResultVO createOrUpdateResult(ResultVO inResult) {
        Result r = new Result();
        r.setName(inResult.getName());
        r.setContract(inResult.getContract());
        r.setNumberOfPlayers(inResult.getNumberOfPlayers());
        this.resultDao.save(r);
        this.resultDao.flush();
        for (Role role : inResult.getContract().getRoles()) {
            short score = (short) inResult.getRoleScores()
                    .stream()
                    .filter((rrv) -> rrv.getRoleName().equalsIgnoreCase(role.getName()))
                    .mapToInt(RoleScoreVO::getScore)
                    .findFirst().orElse(0);
            ResultRole rr = new ResultRole(r, role, score);

            this.resultRoleDao.save(rr);
        }
        this.resultRoleDao.flush();

        inResult.setId(r.getId());

        return inResult;
    }

    public List<ResultVO> findResultsByContractId(Long inContractId) {
        List<Result> lr = this.resultDao.findResultsByContract_Id(inContractId);
        List<ResultVO> returnValue = new ArrayList<>();
        lr.forEach((r) -> {
            ResultVO rv = new ResultVO(r.getName(), r.getContract());
            rv.setId(r.getId());
            this.resultRoleDao.findByResultId(r.getId())
                    .forEach((rr) -> rv.getRoleScores().add(new RoleScoreVO(rr.getRole().getName(), rr.getScore())));
            returnValue.add(rv);
        });
        return returnValue;
    }

    /*
     ***************************************
     * Event methods *
     ***************************************
     */
    public List<Event> findAllEventsSorted() {
        return this.eventDao.findAll(Sort.by(Sort.Direction.DESC, "eventDate"));
    }

    public Event createOrUpdateEvent(Event inEvent) {
        inEvent.setEventDate(LocalDate.now());
        inEvent.setStatus(EventStatus.NEW);
        return this.eventDao.save(inEvent);
    }

    public Event findEventById(Long inId) {
        return this.eventDao.findById(inId).orElse(null);
    }

    public List<DrawVO> getEventDetails(long inEventId) {
        Event e = this.eventDao.findById(inEventId).orElse(null);
        if (e == null) {
            return null;
        }

        List<Draw> ld = this.drawDao.findDrawsByEvent_Id(inEventId);
        List<DrawVO> ldvo = new ArrayList<>();
        if (!ld.isEmpty()) {
            ld.forEach((d) -> {
                DrawVO dvo = new DrawVO();
                dvo.setEvent(e);
                dvo.setContract(d.getContract());
                dvo.setResult(d.getResult());
                List<PlayerDrawVO> lpdVO = new ArrayList<>();
                e.getPlayers().forEach((p) -> {
                    PlayerDrawVO pdVO = new PlayerDrawVO();
                    PlayerDraw pd = this.playerDrawDao.findById(new PlayerDrawPK(p, d)).orElseThrow();
                    pdVO.setPlayerName(p.getName());
                    pdVO.setEventScore(pd.getEventScore());
                    pdVO.setDrawScore(pd.getDrawScore());
                    pdVO.setRoleName(pd.getRole().getName());
                    pdVO.setDealer(pd.isDealer());
                    lpdVO.add(pdVO);
                });
                dvo.setPlayers(lpdVO);
                ldvo.add(dvo);
            });
        }
        return ldvo;
    }

    public List<DrawVO> addDraw(Long inEventId, DrawVO inDraw) {
        Event e = this.eventDao.findById(inEventId).orElse(null);
        if (e == null) {
            return null;
        }
        Contract c = inDraw.getContract();
        Result r = inDraw.getResult();
        List<Player> lp = e.getPlayers();

        // First find the id of the last played Draw
        List<Draw> ldr = this.drawDao.findDrawsByEvent_Id(inEventId);
        Long lastDrawId = -1L;
        if (!ldr.isEmpty()) {
            lastDrawId = ldr.get(ldr.size() - 1).getId();
        }

        // First create a new Draw record
        Draw d = new Draw();
        d.setContract(c);
        d.setEvent(e);
        d.setResult(r);
        d = this.drawDao.save(d);
        this.drawDao.flush();

        // Now update the score of all player participating
        for (Player p : lp) {
            Role playedRole = null;
            boolean dealer = false;
            for (PlayerDrawVO pdVO : inDraw.getPlayers()) {
                if (p.getName().equalsIgnoreCase(pdVO.getPlayerName())) {
                    playedRole = this.roleDao.findRoleByNameEqualsIgnoreCase(pdVO.getRoleName()).orElseThrow();
                    dealer = pdVO.isDealer();
                }
            }
            // Find score for result & role
            ResultRole rr = this.resultRoleDao.findById(new ResultRolePK(playedRole, r)).orElseThrow();
            short score = rr.getScore();
            if (lastDrawId == -1L) {
                // First draw of the event
                PlayerDraw playerDraw = new PlayerDraw(p, d, score, score, playedRole);
                playerDraw.setDealer(dealer);
                this.playerDrawDao.save(playerDraw);
            } else {
                // An old draw have been already played => find the previous scores.
                PlayerDraw lastPlayerDraw = this.playerDrawDao.findById(new PlayerDrawPK(p.getId(), lastDrawId))
                        .orElseThrow();
                short newEventScore = (short) (lastPlayerDraw.getEventScore() + score);
                PlayerDraw playerDraw = new PlayerDraw(p, d, score, newEventScore, playedRole);
                playerDraw.setDealer(dealer);
                this.playerDrawDao.save(playerDraw);
            }
        }
        ;
        this.playerDrawDao.flush();

        e.setStatus(EventStatus.IN_PROGRESS);
        this.eventDao.saveAndFlush(e);

        return this.getEventDetails(inEventId);
    }

    public List<DrawVO> closeEvent(Long inEventId) {
        Event e = this.eventDao.findById(inEventId).orElseThrow();
        e.setStatus(EventStatus.FINISHED);
        this.eventDao.saveAndFlush(e);
        return this.getEventDetails(inEventId);
    }

    public DatabaseCounter getDatabaseCounters() {
        DatabaseCounter dc = new DatabaseCounter();
        dc.setContracts(this.contractDao.count());
        dc.setPlayers(this.playerDao.count());
        dc.setDraws(this.drawDao.count());
        dc.setRoles(this.roleDao.count());
        dc.setEvents(this.eventDao.count());
        dc.setPlayerDraws(this.playerDrawDao.count());
        dc.setResultRoles(this.resultRoleDao.count());
        dc.setResults(this.resultDao.count());

        // Compute players-event records
        List<Event> le = this.eventDao.findAll();
        long playerEvents = 0;
        for (Event e : le) {
            playerEvents += e.getPlayers().size();
        }
        dc.setEventPlayers(playerEvents);

        // Compute contract-role records
        List<Contract> lc = this.contractDao.findAll();
        long contractRoles = 0;
        for (Contract c : lc) {
            contractRoles += c.getRoles().size();
        }
        dc.setContractRoles(contractRoles);

        long totalRecords = dc.getContracts() + dc.getPlayers() + dc.getDraws() + dc.getRoles() + dc.getEvents();
        totalRecords += dc.getPlayerDraws() + dc.getResultRoles() + dc.getResults() + dc.getEventPlayers()
                + dc.getContractRoles();

        dc.setTotalRecords(totalRecords);

        return dc;
    }

    public List<Contract> findContractsForEvent(Long inEventId) {
        Event e = this.eventDao.findById(inEventId).orElse(null);
        if (e == null) {
            return null;
        }

        return this.contractDao.findContractsByNumberOfPlayers(e.getPlayers().size());
    }
}
