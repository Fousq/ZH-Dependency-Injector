package kz.zhanbolat.di.type;

import kz.zhanbolat.di.exception.UniqueBeanNameException;
import kz.zhanbolat.di.type.description.BeanDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BeanSet {
    private List<BeanDescription> beanDescriptions;

    public BeanSet() {
        beanDescriptions = new ArrayList<>();
    }

    public boolean addBean(BeanDescription beanDescription) throws UniqueBeanNameException {
        if (beanDescriptions.stream().anyMatch(existedBean -> existedBean.getBeanName().equals(beanDescription.getBeanName()))) {
            throw new UniqueBeanNameException("The bean with name " + beanDescription.getBeanName() + " already exists");
        }
        return beanDescriptions.add(beanDescription);
    }

    public Stream<BeanDescription> stream() {
        return beanDescriptions.stream();
    }
}
