package assign3;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
    @author metsis
    @author webber
 */

public class ReviewHandlerTestRunner
{
    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(TestReviewHandler.class);

        if(result.getFailures().size() == 0)
        {
            System.out.println("All tests successful !!!");
        }
        else
        {
            System.out.println("No. of failed test cases="+result.getFailures().size());
            for (Failure failure : result.getFailures())
                System.out.println(failure.toString());

        }
    }
}
