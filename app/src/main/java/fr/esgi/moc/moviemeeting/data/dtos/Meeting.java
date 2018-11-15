package fr.esgi.moc.moviemeeting.data.dtos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Meeting implements Serializable {
    
    private int idMeeting;
    private String title;
    private String description;
    private String meetingDate;
    private String creationDate;
    private int idMovie;



    private transient List<User> participants;


    public Meeting(){

    }

    public Meeting(MeetingFromApi meeting){
        this.idMeeting = meeting.getIdMeeting();
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        this.meetingDate = meeting.getMeetingDate();
        this.creationDate = meeting.getCreationDate();
        this.idMovie = meeting.getIdMovie();

        this.participants = meeting.getUsers();
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


    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

}
