package com.ueueo.authorization.permissions;

import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;
import com.ueueo.simplestatechecking.ISimpleStateChecker;
import com.ueueo.simplestatechecking.SimpleStateCheckerContext;
import com.ueueo.users.ICurrentUser;

/**
 * @author Lee
 * @date 2022-05-29 18:02
 */
public class RequireAuthenticatedSimpleStateChecker<TState extends IHasSimpleStateCheckers<TState>> implements ISimpleStateChecker<TState> {

    private ICurrentUser currentUser;

    public RequireAuthenticatedSimpleStateChecker(ICurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public boolean isEnabled(SimpleStateCheckerContext<TState> context) {
        return currentUser.isAuthenticated();
    }

}
