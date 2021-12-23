package in.home.user.service.criteria;

import in.home.user.api.query.user.UserQuery;
import in.home.user.service.custom.UserRepositoryCustom;
import in.home.user.service.entity.UserEntity;
import in.home.user.service.entity.UserEntity_;
import in.home.user.service.framework.BaseEntity_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

  @Autowired private EntityManager entityManager;

  @Override
  public Long count(UserQuery userQuery) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
    criteriaQuery.select(criteriaBuilder.count(root));

    createCriteriaQuery(criteriaBuilder, criteriaQuery, root, userQuery);

    TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
    return query.getSingleResult();
  }

  @Override
  public List<UserEntity> fetch(UserQuery userQuery) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
    Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
    criteriaQuery.select(root);

    createCriteriaQuery(criteriaBuilder, criteriaQuery, root, userQuery);

    TypedQuery<UserEntity> query = entityManager.createQuery(criteriaQuery);
    if (userQuery.getPagination().getOffset() != null
        && userQuery.getPagination().getLimit() != null) {
      return query
          .setFirstResult(userQuery.getPagination().getOffset().intValue())
          .setMaxResults(userQuery.getPagination().getLimit().intValue())
          .getResultList();
    } else {
      return query.getResultList();
    }
  }

  private <T> void createCriteriaQuery(
      CriteriaBuilder criteriaBuilder,
      CriteriaQuery<T> criteriaQuery,
      Root<UserEntity> root,
      UserQuery userQuery) {

    if (!CollectionUtils.isEmpty(userQuery.getUserFilter().getIdList())) {
      criteriaQuery.where(root.get(BaseEntity_.ID).in(userQuery.getUserFilter().getIdList()));
    }
    if (!CollectionUtils.isEmpty(userQuery.getUserFilter().getUserNameListIn())) {
      criteriaQuery.where(
          root.get(UserEntity_.USER_NAME).in(userQuery.getUserFilter().getUserNameListIn()));
    }
    if (!CollectionUtils.isEmpty(userQuery.getUserFilter().getUserNameListLike())) {
      CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(root.get(UserEntity_.USER_NAME));
      for (String username : userQuery.getUserFilter().getUserNameListLike()) {
        inClause.value(criteriaBuilder.like(root.get(UserEntity_.USER_NAME), "%" + username + "%"));
      }
    }
    if (!CollectionUtils.isEmpty(userQuery.getUserFilter().getEmailList())) {
      criteriaQuery.where(root.get(UserEntity_.EMAIL).in(userQuery.getUserFilter().getEmailList()));
    }
    if (userQuery.getUserFilter().getActive() != null) {
      criteriaQuery.where(
          criteriaBuilder.equal(
              root.get(BaseEntity_.ACTIVE), userQuery.getUserFilter().getActive()));
    }
  }
}
