//package com.example.chuyentrang.service;
//
//import com.example.chuyentrang.model.Project;
//import com.example.chuyentrang.repository.ProjectRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class ProjectService {
//    private ProjectRepository projectRepository;
//
//    // Create
//    public Project createProject(Project project) {
//        return projectRepository.save(project);
//    }
//
//    // Read
//    public Optional<Project> getProjectById(int id) {
//        return projectRepository.findById(id);
//    }
//
//    public Iterable<Project> getAllProjects() {
//        return projectRepository.findAll();
//    }
//
//    // Update
//    public Project updateProject(int id, Project updatedProject) {
//        return projectRepository.findById(id)
//                .map(existingProject -> {
//                    // Update fields here
//                    return projectRepository.save(existingProject);
//                }).orElseThrow(() -> new RuntimeException("Project not found"));
//    }
//
//    // Delete
//    public void deleteProject(int id) {
//        projectRepository.deleteById(id);
//    }
//}
