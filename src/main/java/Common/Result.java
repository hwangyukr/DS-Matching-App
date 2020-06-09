package Common;

public class Result {
    private String msg;
    private boolean success;

    public Result(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public Result() {

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
