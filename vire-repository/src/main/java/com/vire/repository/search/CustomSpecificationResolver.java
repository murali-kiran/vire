package com.vire.repository.search;

import org.springframework.data.jpa.domain.Specification;

public class CustomSpecificationResolver<T> {
  private String searchString;

  public CustomSpecificationResolver(String searchString) {
    this.searchString = searchString;
  }

  public Specification<T> resolve() {
    CriteriaParser parser = new CriteriaParser();
    var specBuilder = new CustomSpecificationsBuilder<T>();
    return specBuilder.build(parser.parse(this.searchString), CustomSpecification<T>::new);
  }
}
