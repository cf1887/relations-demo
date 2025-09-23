package dev.cf1887.relations_demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.cf1887.relations_demo.dao.ProjectRepository;
import dev.cf1887.relations_demo.dto.ProjectCreateRequest;
import dev.cf1887.relations_demo.dto.ProjectResponse;
import dev.cf1887.relations_demo.entity.Project;
import dev.cf1887.relations_demo.exception.DuplicateProjectNameException;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository theProjectRepository) {
        this.projectRepository = theProjectRepository;
    }

    /**
     * Service method to create a project entity from a given dto
     * 
     * @param dto
     * @see Project
     * @return
     */
    @Transactional
    public Project create(ProjectCreateRequest dto) {
        final String name = dto.getName().trim();

        // Duplicate check before inserting
        projectRepository.findByName(name).ifPresent(
                project -> {
                    throw new DuplicateProjectNameException(name);
                });

        // Save the dto
        Project tempProject = new Project();
        tempProject.setName(name);
        return projectRepository.save(tempProject);
    }

    /**
     * Service method to get all currently available projects as a list of dto
     * (ProjectResponse)
     * 
     * @see ProjectResponse
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProjectResponse> list() {
        return projectRepository.findAll()
                .stream()
                .map(p -> new ProjectResponse(p.getId(), p.getName()))
                .toList();
    }
}
