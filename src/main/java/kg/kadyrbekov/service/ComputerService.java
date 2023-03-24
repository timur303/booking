package kg.kadyrbekov.service;

import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.ComputerRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComputerService {

    private final UserRepository userRepository;

    private final ComputerRepository computerRepository;

    private final ClubRepository clubRepository;
}
