package com.game.data;

import java.util.List;

public class BaseInfo {
	
	private static List<RoleData> rolelist;

	public static List<RoleData> getRolelist() {
		return rolelist;
	}

	public static void setRolelist(List<RoleData> rolelist) {
		BaseInfo.rolelist = rolelist;
	}
	
}
