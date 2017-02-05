package async.controller;

import async.GithubLookupService;
import async.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by shengfalin on 2/4/17.
 */
@RestController
public class GithubLookupController {

    private GithubLookupService githubLookupService;

    @Autowired
    public GithubLookupController(GithubLookupService githubLookupService) {
        this.githubLookupService = githubLookupService;
    }
//
//    @RequestMapping("/users/{username}")
//    public User findUser(@PathVariable String username) throws InterruptedException, ExecutionException {
//        return githubLookupService.findUser(username).get();
//    }

    @RequestMapping("/users/{usernameList}")
    public List<User> findUsers(@PathVariable List<String> usernameList) throws InterruptedException, ExecutionException {
        return githubLookupService.findUsers(usernameList).get();
    }

}
