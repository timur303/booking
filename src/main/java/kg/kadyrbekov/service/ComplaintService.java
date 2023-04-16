package kg.kadyrbekov.service;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Complaint;
import kg.kadyrbekov.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final AdminNotificationService adminNotificationService;

    public ComplaintService(ComplaintRepository complaintRepository, AdminNotificationService adminNotificationService) {
        this.complaintRepository = complaintRepository;
        this.adminNotificationService = adminNotificationService;
    }

    public void createComplaint(User user, String message) {
        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setMessage(message);
        complaint.setCreatedAt(LocalDateTime.now());

        complaintRepository.save(complaint);

        // Send notification to admin
        adminNotificationService.notifyAdmin(complaint);
    }
}

