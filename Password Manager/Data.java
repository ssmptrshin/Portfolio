public class Data {
    Data(String user, String password) {
        _user = user;
        _password = password;
    }

    public String get_user() {
        return _user;
    }

    public String get_password() {
        return _password;
    }

    private String _user;
    private String _password;
}
