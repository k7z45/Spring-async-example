package async;

/**
 * Created by shengfalin on 2/4/17.
 */
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubLookupService {

    private static final Logger logger = LoggerFactory.getLogger(GithubLookupService.class);

    private final AsyncRestTemplate restTemplate;

    @Autowired
    public GithubLookupService(AsyncRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<User> findUser(String user) {
        logger.info("Looking up " + user);
        Random rand = new Random();
        int  n = rand.nextInt(1000) + 1000;
        User result = new User(user, user + "'s blog has random number: " + n);

        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Simulate async and call completedFuture to "provide" mock result
        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<List<User>> findUsers(List<String> usernameList) throws InterruptedException {
        logger.info("Looking up " + usernameList);
        CompletableFuture results = sequence(usernameList.parallelStream().map(user -> findUser(user)).collect(Collectors.toList()));
        //Simulate async and call completedFuture to "provide" mock result
        return results;
    }

    private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(future -> future.join()).
                        collect(Collectors.<T>toList())
        );
    }
}