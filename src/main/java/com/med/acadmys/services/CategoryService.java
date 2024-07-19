package com.med.acadmys.services;

import com.med.acadmys.dtos.ChildCategoryDto;
import com.med.acadmys.dtos.ParentCategoryDto;
import com.med.acadmys.dtos.Response;
import com.med.acadmys.models.ChildCategory;
import com.med.acadmys.models.ParentCategory;
import com.med.acadmys.repositories.ChildCategoryRepository;
import com.med.acadmys.repositories.ParentCategoryRepository;
import com.med.acadmys.utils.ResponseUtil;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ParentCategoryRepository parentCategoryRepository;
    private final ChildCategoryRepository childCategoryRepository;
    private final ModelMapper modelMapper;
    private final ResponseUtil responseUtil;

    @Transactional
    public Response saveParentCategory(ParentCategory parentCategory) {
        try {
            ParentCategory savedParentCategory = parentCategoryRepository.save(parentCategory);
            Type type = new TypeToken<ParentCategoryDto>() {
            }.getType();
            ParentCategoryDto response = modelMapper.map(savedParentCategory, type);
            return responseUtil.generateSuccessResponse(response);
        } catch (Exception e) {
            return responseUtil.generateFailureResponse(e.getLocalizedMessage());
        }
    }

    @Transactional
    public Response updateParentCategory(ParentCategory parentCategory) {
        Optional<ParentCategory> savedParentCategory = parentCategoryRepository.findById(parentCategory.getId());
        if (savedParentCategory.isPresent()) {
            ParentCategory parentCategory1 = parentCategoryRepository.save(parentCategory);
            Type type = new TypeToken<ParentCategoryDto>() {
            }.getType();
            ParentCategoryDto response = modelMapper.map(parentCategory1, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No Category Found please add first.");
        }
    }

    @Transactional(readOnly = true)
    public Response getParentCategoryById(Long id) {
        Optional<ParentCategory> parentCategory = parentCategoryRepository.findById(id);
        if (parentCategory.isPresent()) {
            Type type = new TypeToken<ParentCategoryDto>() {
            }.getType();
            ParentCategoryDto response = modelMapper.map(parentCategory, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No Parent Category Found.");
        }
    }

    @Transactional(readOnly = true)
    public Response getParentCategoryByName(String name) {
        Optional<ParentCategory> parentCategory = parentCategoryRepository.findByName(name);
        if (parentCategory.isPresent()) {
            Type type = new TypeToken<ParentCategoryDto>() {
            }.getType();
            ParentCategoryDto response = modelMapper.map(parentCategory, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No Parent Category Found.");
        }
    }

    @Transactional(readOnly = true)
    public Response getAllParentCategories() {
        List<ParentCategory> parentCategories = parentCategoryRepository.findAll();
        if (!parentCategories.isEmpty()) {
            Type type = new TypeToken<List<ParentCategoryDto>>() {
            }.getType();
            List<ParentCategoryDto> response = modelMapper.map(parentCategories, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No Parent Category Found.");
        }
    }

    @Transactional
    public Response deleteParentCategory(Long id) {
        try {
            parentCategoryRepository.deleteById(id);
            return responseUtil.generateSuccessResponse("Parent Category with all of its Child Categories deleted Successfully");
        } catch (Exception e) {
            return responseUtil.generateFailureResponse(e.getLocalizedMessage());
        }
    }

    @Transactional
    public Response saveChildCategory(Long parentCategoryId, ChildCategory childCategory) {
        try {
            Optional<ParentCategory> parentCategory = parentCategoryRepository.findById(parentCategoryId);
            if (parentCategory.isPresent()) {
                Set<ChildCategory> childCategories = parentCategory.get().getChildCategory();
                childCategories.add(childCategory);
                parentCategory.get().setChildCategory(childCategories);
                ParentCategory savedParentCategory = parentCategoryRepository.save(parentCategory.get());
                Type type = new TypeToken<ParentCategoryDto>() {
                }.getType();
                ParentCategoryDto response = modelMapper.map(savedParentCategory, type);
                return responseUtil.generateSuccessResponse(response);
            } else {
                return responseUtil.generateFailureResponse("Failed to create Child Category, you need to create the parent category first");
            }
        } catch (Exception e) {
            return responseUtil.generateFailureResponse(e.getLocalizedMessage());
        }
    }

    @Transactional
    public Response updateChildCategory(ChildCategory childCategory) {
        Optional<ChildCategory> fetchedChildCategory = childCategoryRepository.findById(childCategory.getId());
        if(fetchedChildCategory.isPresent()) {
            ChildCategory updatedChildCategory = childCategoryRepository.save(childCategory);
            Type type = new TypeToken<ChildCategoryDto>() {
            }.getType();
            ChildCategoryDto response = modelMapper.map(updatedChildCategory, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No Child Category found.");
        }
    }

    @Transactional(readOnly = true)
    public Response getChildCategoryById(Long id) {
        Optional<ChildCategory> childCategory = childCategoryRepository.findById(id);
        if (childCategory.isPresent()) {
            Type type = new TypeToken<ChildCategoryDto>() {
            }.getType();
            ChildCategoryDto response = modelMapper.map(childCategory, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No Child Category Found.");
        }
    }

    @Transactional(readOnly = true)
    public Response getChildCategoryByName(String name) {
        Optional<ChildCategory> childCategory = childCategoryRepository.findByName(name);
        if (childCategory.isPresent()) {
            Type type = new TypeToken<ChildCategoryDto>() {
            }.getType();
            ChildCategoryDto response = modelMapper.map(childCategory, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No Child Category Found.");
        }
    }

    @Transactional
    public Response deleteChildCategory(Long id) {
        try {
            childCategoryRepository.deleteById(id);
            return responseUtil.generateSuccessResponse("Child Category deleted Successfully");
        } catch (Exception e) {
            return responseUtil.generateFailureResponse(e.getLocalizedMessage());
        }
    }
}