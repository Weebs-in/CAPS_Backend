package sg.edu.nus.iss.caps.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Cooper Liu
 * @Description: Common response body
 * @Date: Created at 23:35 2023/6/14
 * @Modified by:
 */

public class R extends HashMap<String, Object> {

    public R() {
        put("code", RCode.CODE_SUCCESS);
        put("msg", RMessage.SUCCESS);
    }

    public boolean isOk() {
        int code = (Integer) get("code");
        return code == RCode.CODE_SUCCESS;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static R ok() {
        return new R();
    }

    public static R ok(Object data) {
        R r = new R();
        r.put("data", data);
        return r;
    }

    public static R ok(String message) {
        R r = new R();
        r.put("msg", message);
        return r;
    }

    public static R ok(int code, String message) {
        R r = new R();
        r.put("msg", message);
        r.put("code", code);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("msg", msg);
        r.put("code", code);
        return r;
    }

    public static R error(String msg) {
        return error(RCode.CODE_FAILED, msg);
    }

    public static R error() {
        return error(RCode.CODE_FAILED, RMessage.FAILED);
    }
}
