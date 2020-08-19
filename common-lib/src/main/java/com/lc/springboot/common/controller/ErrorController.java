package com.lc.springboot.common.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.lc.springboot.common.api.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 异常处理类（主要处理http错误码）
 *
 * @author liangchao
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {
  private final ErrorProperties errorProperties;

  public ErrorController() {
    this(new DefaultErrorAttributes(), new ErrorProperties());
  }

  /**
   * Create a new {@link ErrorController} instance.
   *
   * @param errorAttributes the error attributes
   * @param errorProperties configuration properties
   */
  public ErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
    this(errorAttributes, errorProperties, Collections.emptyList());
  }

  /**
   * Create a new {@link ErrorController} instance.
   *
   * @param errorAttributes the error attributes
   * @param errorProperties configuration properties
   * @param errorViewResolvers error view resolvers
   */
  public ErrorController(
      ErrorAttributes errorAttributes,
      ErrorProperties errorProperties,
      List<ErrorViewResolver> errorViewResolvers) {
    super(errorAttributes, errorViewResolvers);
    Assert.notNull(errorProperties, "ErrorProperties must not be null");
    this.errorProperties = errorProperties;
  }

  @Override
  public String getErrorPath() {
    return this.errorProperties.getPath();
  }

  @RequestMapping(produces = "text/html")
  public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
    HttpStatus status = getStatus(request);
    Map<String, Object> model =
        Collections.unmodifiableMap(
            getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
    response.setStatus(status.value());
    ModelAndView modelAndView = resolveErrorView(request, response, status, model);
    return (modelAndView != null ? modelAndView : new ModelAndView("error", model));
  }

  @RequestMapping
  @ResponseBody
  public BaseResponse error(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> body =
        getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
    HttpStatus status = getStatus(request);

    // 强制将任何其他状态码改成200，为了适应前端的架构
    response.setStatus(HttpStatus.OK.value());

    Object message = body.get("message");
    if (ObjectUtils.isNotEmpty(body)
        && message != null
        && !StringUtils.equals(message.toString(), "No message available")) {
      return BaseResponse.failure(message.toString());
    }

    //返回http状态码 对象的信息
    return BaseResponse.failure(status.toString());

    // int httpStatusCode = status.value();
    // String msg = HttpStatus_CN.codeMap.get(httpStatusCode);
    //
    // // 如果能从配置文件从找到相关的错误信息，则以该错误信息为准
    // if (StringUtils.isNotBlank(msg)) {
    //   return BaseResponse.failure(msg);
    // }

    // if (status.is1xxInformational()) {
    //   msg = ResultCode.PROVISIONAL_RESPONSE.getMsg();
    // } else if (status.is3xxRedirection()) {
    //   msg = ResultCode.REDIRECT.getMsg();
    // } else if (status.is4xxClientError()) {
    //   msg = ResultCode.NOT_FOUND.getMsg();
    // } else if (status.is5xxServerError()) {
    //   msg = ResultCode.INTERNAL_SERVER_ERROR.getMsg();
    // } else {
    //   msg = ResultCode.INTERNAL_SERVER_ERROR.getMsg();
    // }
    // return BaseResponse.failure(msg);
  }

  /**
   * Determine if the stacktrace attribute should be included.
   *
   * @param request the source request
   * @param produces the media type produced (or {@code MediaType.ALL})
   * @return if the stacktrace attribute should be included
   */
  protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
    ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
    if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
      return true;
    }
    if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
      return getTraceParameter(request);
    }
    return false;
  }

  /**
   * Provide access to the error properties.
   *
   * @return the error properties
   */
  protected ErrorProperties getErrorProperties() {
    return this.errorProperties;
  }
}
