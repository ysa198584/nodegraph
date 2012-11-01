package com.dnt.graph.biz.common.constants;

/**
 * 错误详细信息
 * 
 * @author ting.weit
 */
public enum Errors {

    SUCCESS(0, ""),
    FILE_PATH_ERROR(5000,"文件路径错误"),
    FILE_NOT_FIND_ERROR(5001,"没有找到文件"),
    READ_FILE_ERROR(5002,"读取目标文件出错"),
    LOAD_REMOTE_FILE_FAILURE(5003,"下载远程文件出错"),
    NOTLOGGEDIN(3000, "未登录"),
    USER_LOGIN_ID_IS_NULL(3001, "登录失败，帐户密码不一致"), 
    USER_LOGIN_ID_NOT_EXIST(3002, "账号不存在或已注销"),
    USER_0LD_PASSWORD_WRONG(3004, "原密码密码不正确"),
    USER_PASSWORD_ERROR(3011, "新密码密码不正确"),
    USER_NOT_EXIST(3005, "用户不存在"),
    ROLE_NAME_EXIST(3006, "角色名称已经存在"), 
    APP_ID_EXIST(3007, "应用ID已经存在"), 
    APP_NAME_EXIST(3008, "应用名称已经存在"),
    APP_IN_USER(3009, "应用名称已经被使用,不能删除"),
    NOPERMISSION(3010, "用户无权限"),
    RES_ID_NOT_EXIST(3020, "资源ID不存在或已失效"), 
    RES_PARENTID_NOT_EXIST(3021, "资源父ID不存在"), 
    RES_CODE_EXIST(3022,"资源CODE已被使用或为空"),
    USER_NOT_HAVE_SAME_TEAM_PERNISSION(3009, "操作用户不存在或者不属于您所在的团队"),
    MALFORMED_URL_EXCEPTION(3010, "资源URL格式化异常"),
    DATA_OBJECT_EXCEPTION(3030, "数据对象异常");

    private int    code;
    private String message;

    Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code + ":" + message;
    }
}
