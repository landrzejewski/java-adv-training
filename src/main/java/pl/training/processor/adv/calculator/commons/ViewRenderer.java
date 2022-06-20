package pl.training.processor.adv.calculator.commons;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ViewRenderer {

    private final ViewResolver viewResolver;

    public void render(ModelAndView modelAndView) {
        var viewName = modelAndView.viewName();
        var data = modelAndView.data();
        viewResolver.resolve(viewName).render(data);
    }

}
