package Controller;

import Model.PIMEntity;

import java.util.Collection;
import java.util.Date;

public interface RemotePIMCollection<T> {
    Collection<T> getNotes();
    Collection<T> getNotes(String owner);
    Collection<T> getTodos();
    Collection<T> getTodos(String owner);
    Collection<T> getAppointments();
    Collection<T> getAppointments(String owner);
    Collection<T> getContacts();
    Collection<T> getContacts(String owner);
    Collection<T> getItemsForDate(Date d);
    Collection<T> getItemsForDate(Date d, String owner);
    Collection<T> getAll();
    Collection<T> getAllByOwner(String owner);
    boolean addItems(PIMEntity entity);
}
