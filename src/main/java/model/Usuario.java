package model;

public class Usuario extends Cliente{
    public String password;
    public int userType;

    public Usuario(String name, String password, String cpf, String birthDate, int userType) {
        super(cpf,name,birthDate);
        this.setPassword(password);
        this.setUserType(userType);
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
