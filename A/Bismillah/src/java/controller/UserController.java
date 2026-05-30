package controller;

public class UserController extends BaseController {

    public boolean hapusUser(int id) throws Exception {
        validatePositiveId(id, "ID user");
        return executeUpdate("DELETE FROM `user` WHERE id = ?", id) > 0;
    }
}
