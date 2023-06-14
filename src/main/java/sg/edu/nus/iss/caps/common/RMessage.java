package sg.edu.nus.iss.caps.common;

/**
 * @Author: Cooper Liu
 * @Description: Common response message
 * @Date: Created at 23:36 2023/6/14
 * @Modified by:
 */

public abstract class RMessage {
    // General Response Message
    public static final String SUCCESS = "Action Successful";
    public static final String FAILED = "Action Failed";

    // Specific Response Message
    public static final String CREATE_SUCCESS = "Create Successful";
    public static final String CREATE_FAILED = "Create Failed";
    public static final String RETRIEVE_SUCCESS = "Retrieve Successful";
    public static final String RETRIEVE_FAILED = "Retrieve Failed";
    public static final String UPDATE_SUCCESS = "Update Successful";
    public static final String UPDATE_FAILED = "Update Failed";
    public static final String DELETE_SUCCESS = "Delete Successful";
    public static final String DELETE_FAILED = "Delete Failed";
}
