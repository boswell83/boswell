package server.skills;

public enum MobActions {
	
	MOVE(0),
    STAND(1),
    JUMP(2),
    FLY(3),
    ROPE(4),
    REGEN(5),
    BOMB(6),
    HIT1(7),
    HIT2(8),
    HITF(9),
    DIE1(10),
    DIE2(11),
    DIEF(12),
    ATTACK1(13),
    ATTACK2(14),
    ATTACK3(15),
    ATTACK4(16),
    ATTACK5(17),
    ATTACK6(18),
    ATTACK7(19),
    ATTACK8(20),
    ATTACKF(21),
    SKILL1(22),
    SKILL2(23),
    SKILL3(24),
    SKILL4(25),
    SKILL5(26),
    SKILL6(27),
    SKILL7(28),
    SKILL8(29),
    SKILL9(30),
    SKILL10(31),
    SKILL11(32),
    SKILL12(33),
    SKILL13(34),
    SKILL14(35),
    SKILL15(36),
    SKILL16(37),
    ENABLED(37),
    SKILLF(38),
    CHASE(39),
    MISS(40),
    SAY(41),
    EYE(42),
    NO(43);
	
	private int action;
	
	private MobActions(int action) {
		this.action = action;
	}

	public int getAction() {
		return action;
	}
	
	public MobActions getAction(int action) {
		for (MobActions act : MobActions.values()) {
			if (act.getAction() == action) {
				return act;
			}
		}
		return null;
	}
	
}
