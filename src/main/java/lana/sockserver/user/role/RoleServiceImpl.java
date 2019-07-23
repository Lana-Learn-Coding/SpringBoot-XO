package lana.sockserver.user.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    @Autowired
    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Integer getId(Role role) {
        switch (role) {
            case ADMIN:
                return 1;
            case USER:
                return 2;
            default:
                return 3;
        }
    }

    @Override
    public RoleEntity get(Role role) {
        // Default Entity for Guest. Based on database
        // This value can be change if the db role table changed
        RoleEntity guestDefault = new RoleEntity();
        guestDefault.setId(3);
        guestDefault.setRoleName("GUEST");

        return roleRepo.findById(getId(role)).orElse(guestDefault);
    }
}
