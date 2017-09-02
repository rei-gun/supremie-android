package utils.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POSTResponseOrder {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     *
     * @param statusCode
     * The Status Code
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The Level
     */
    public void setMessage(String message) {
        this.message = message;

    }

    public POSTResponseOrder(Integer id, Integer statusCode, String message) {
        this.id = id;
        this.statusCode = statusCode;
        this.message = message;
    }

}