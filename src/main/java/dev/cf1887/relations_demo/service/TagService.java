package dev.cf1887.relations_demo.service;

import org.springframework.stereotype.Service;

import dev.cf1887.relations_demo.dao.TagRepository;
import dev.cf1887.relations_demo.dto.TagCreateRequest;
import dev.cf1887.relations_demo.dto.TagResponse;
import dev.cf1887.relations_demo.entity.Tag;
import dev.cf1887.relations_demo.exception.DuplicateTagNameException;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository theTagRepository) {
        this.tagRepository = theTagRepository;
    }

    public TagResponse create(TagCreateRequest dto) {
        final String name = dto.getName().trim();
        // Check, if name already exists
        tagRepository.findByName(name).ifPresent(tag -> {
            throw new DuplicateTagNameException(name);
        });
        // Create a new Tag instance
        Tag tempTag = new Tag();
        tempTag.setName(name);
        // Save the new Tag to the database
        Tag saved = tagRepository.save(tempTag);
        return new TagResponse(saved.getId(), saved.getName());
    }
}
