package in.home.user.service.criteria;

import in.home.user.api.query.role.RoleQuery;
import in.home.user.service.custom.RoleRepositoryCustom;
import in.home.user.service.entity.RoleEntity;
import in.home.user.service.entity.RoleEntity_;
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
public class RoleRepositoryImpl implements RoleRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Long count(RoleQuery roleQuery) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);
        criteriaQuery.select(criteriaBuilder.count(root));

        createCriteriaQuery(criteriaBuilder, criteriaQuery, root, roleQuery);

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public List<RoleEntity> fetch(RoleQuery roleQuery) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
        Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);
        criteriaQuery.select(root);

        createCriteriaQuery(criteriaBuilder, criteriaQuery, root, roleQuery);

        TypedQuery<RoleEntity> query = entityManager.createQuery(criteriaQuery);
        if (roleQuery.getPagination().getOffset() != null &&
            roleQuery.getPagination().getLimit() != null) {
            return query.setFirstResult(roleQuery.getPagination().getOffset().intValue())
                        .setMaxResults(roleQuery.getPagination().getLimit().intValue())
                        .getResultList();
        }
        else {
            return query.getResultList();
        }
    }

    private <T> void createCriteriaQuery(CriteriaBuilder criteriaBuilder,
                                         CriteriaQuery<T> criteriaQuery, Root<RoleEntity> root,
                                         RoleQuery roleQuery) {

        if (!CollectionUtils.isEmpty(roleQuery.getRoleFilter().getIdList())) {
            criteriaQuery.where(root.get(BaseEntity_.ID).in(roleQuery.getRoleFilter().getIdList()));
        }
        if (!CollectionUtils.isEmpty(roleQuery.getRoleFilter().getRoleNameListIn())) {
            criteriaQuery.where(root.get(RoleEntity_.ROLE_NAME)
                                    .in(roleQuery.getRoleFilter().getRoleNameListIn()));
        }
        if (!CollectionUtils.isEmpty(roleQuery.getRoleFilter().getRoleNameListLike())) {
            CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(
                    root.get(RoleEntity_.ROLE_NAME));
            for (String roleName : roleQuery.getRoleFilter().getRoleNameListLike()) {
                inClause.value(criteriaBuilder.like(root.get(RoleEntity_.ROLE_NAME),
                                                    "%" + roleName + "%"));
            }
        }
        if (roleQuery.getRoleFilter().getActive() != null) {
            criteriaQuery.where(criteriaBuilder.equal(root.get(BaseEntity_.ACTIVE),
                                                      roleQuery.getRoleFilter().getActive()));
        }
    }
}
