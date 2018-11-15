package fr.esgi.moc.moviemeeting.data.dtos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MeetingFromApi implements Serializable {

    private int idMeeting;
    private String title;
    private String description;
    private String meetingDate;
    private String creationDate;
    private int idMovie;


    @SerializedName("Users")
    private List<User> users;



    public MeetingFromApi(){

    }



    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}
