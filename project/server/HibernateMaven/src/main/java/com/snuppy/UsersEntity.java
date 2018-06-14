package com.snuppy;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "users", schema = "snip_db", catalog = "")
public class UsersEntity {
    private int idUser;
    private String userEmail;
    private String userPassword;
    private byte[] imageBlob;

    public UsersEntity(String userEmail, String userPassword, byte[] imageBlob) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.imageBlob = imageBlob;
    }

    public UsersEntity() {
    }

    @Id
    @Column(name = "id_user")
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "user_email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "user_password")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "image_blob")
    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (idUser != that.idUser) return false;
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) return false;
        if (userPassword != null ? !userPassword.equals(that.userPassword) : that.userPassword != null) return false;
        if (!Arrays.equals(imageBlob, that.imageBlob)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(imageBlob);
        return result;
    }
}
