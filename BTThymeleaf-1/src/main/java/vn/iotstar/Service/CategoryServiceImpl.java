package vn.iotstar.Service;

import vn.iotstar.Entity.Category;
import vn.iotstar.Repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.util.StringUtils;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

	@Override
    public <S extends Category> S save(S entity) {
    if(entity.getCategoryID() == 0) {
    return categoryRepository.save(entity);
    }else {
    	Optional<Category> opt = findById(entity.getCategoryID());
    if(opt.isPresent()) {
    if (StringUtils.isEmpty(entity.getImages())) {
    	entity.setImages(opt.get().getImages());
    }else {
	    //lấy lại images cũ
	    entity.setImages(entity.getImages());
	    }
    }
    return categoryRepository.save(entity);
    }
	}

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findByCategoryNameContaining(String name) {
        if (name == null || name.trim().isEmpty()) {
            return categoryRepository.findAll();
        }
        return categoryRepository.findByCategoryNameContainingIgnoreCase(name);
    }

    @Override
    public Page<Category> findByCategoryNameContaining(String name, Pageable pageable) {
        if (name == null || name.trim().isEmpty()) {
            return categoryRepository.findAll(pageable);
        }
        return categoryRepository.findByCategoryNameContainingIgnoreCase(name, pageable);
    }
    @Override
    public Optional<Category> findByCategoryName(String name) {
    return categoryRepository.findByCategoryName(name);
    }
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
    	this.categoryRepository = categoryRepository;
    	}
	@Override
	public void delete(Category entity) {
		categoryRepository.delete(entity);

		
	}

	@Override
	public long count() {
		return categoryRepository.count();

	}

	@Override
	public <S extends Category> Optional<S> findOne(Example<S> example) {
		return categoryRepository.findOne(example);
	}

	@Override
	public List<Category> findAllById(Iterable<Integer> ids) {
		return categoryRepository.findAllById(ids);
	}

	@Override
	public List<Category> findAll(Sort sort) {
		return categoryRepository.findAll(sort);
	}
}