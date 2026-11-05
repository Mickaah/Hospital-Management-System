
package hospitalmanagementsystem.dsa;

import hospitalmanagementsystem.model.Appointments;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class AppointmentPriorityQueue {

    private PriorityQueue<Appointments> appointmentQueue;

    public AppointmentPriorityQueue() {

        appointmentQueue = new PriorityQueue<>();
    }

    // Adds an appointment to the queue
    public void addAppointment(Appointments appointment) {

        if (appointment != null) {
            appointmentQueue.offer(appointment);
        }
    }

    // Gets and removes the highest-priority appointment
    public Appointments processNextAppointment() {

        return appointmentQueue.poll();
    }

    // Views the highest-priority appointment
    public Appointments peekNextAppointment() {

        return appointmentQueue.peek();
    }

    // Checks whether the queue is empty
    public boolean isEmpty() {

        return appointmentQueue.isEmpty();
    }

    // Returns appointments sorted by priority
    public List<Appointments> getAllAppointments() {

        List<Appointments> list =
                new ArrayList<>(appointmentQueue);

        Collections.sort(list);

        return list;
    }

    // Returns the number of queued appointments
    public int size() {

        return appointmentQueue.size();
    }

    // Removes an appointment from the queue
    public boolean removeAppointment(Appointments appointment) {

        return appointmentQueue.remove(appointment);
    }
}
