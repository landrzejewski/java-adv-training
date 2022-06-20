package pl.training.processor.adv.calculator.commons;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.RequiredArgsConstructor;

@Controller
@Interceptor
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Dispatcher {

    private final ViewRenderer viewRenderer;

    @AroundInvoke
    public Object dispatch(InvocationContext invocationContext) throws Exception {
        var result = invocationContext.proceed();
        if (result instanceof ModelAndView modelAndView) {
            viewRenderer.render(modelAndView);
        }
        return result;
    }

}
