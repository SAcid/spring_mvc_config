package org.sacid.spring_mvc_config.filter;

import java.io.IOException;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

//public class ContentCachingRequestWrapperFilter implements Filter {
@Component
public class ContentCachingRequestWrapperFilter extends OncePerRequestFilter {

  private static final Set<HttpMethod> METHODS = Set.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE);

//  @Override
//  public void doFilterInternal(HttpServletRequest request, HttpServletRequest response, FilterChain filterChain)
//      throws IOException, ServletException {
//    if(METHODS.contains(HttpMethod.valueOf(request.getMethod()))){
//      filterChain.doFilter(new ContentCachingRequestWrapper(request), response);
//      return;
//    }
//    filterChain.doFilter(request, response);
//  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if(METHODS.contains(HttpMethod.valueOf(request.getMethod()))){
      filterChain.doFilter(new ContentCachingRequestWrapper(request), response);
      return;
    }
    filterChain.doFilter(request, response);

  }
}
