package io.github.tuyendev.msv.common.constant;

import java.util.List;
import java.util.Map;

public enum AuthorityType {


	ADMIN("admin", "app.common.authority.label.admin"),
	EDITOR("editor", "app.common.authority.label.editor"),
	USER("user", "app.common.authority.label.user");

	public static final String ADMIN_VALUE = "admin";

	public static final String EDITOR_VALUE = "editor";

	public static final String USER_VALUE = "user";

	private static final Map<String, AuthorityType> data = Map.of(
			ADMIN.value(), ADMIN,
			EDITOR.value, EDITOR,
			USER.value, USER);

	final String value;

	final String desc;

	AuthorityType(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static AuthorityType typeOf(String value) {
		return data.get(value);
	}

	public static List<String> names() {
		return List.of(ADMIN.value, EDITOR.value, USER.value);
	}

	public String value() {
		return value;
	}

	public String desc() {
		return desc;
	}
}
