import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class CalculateServlet extends HttpServlet {
 
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
 
    // 获取表达式
    String expression = request.getParameter("expression");
 
    // 进行计算
    Map<String, Object> result = new HashMap<>();
 
    // 校验表达式
    if (!Validator.validateExpression(expression)) {
      result.put("error", "表达式错误");
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.print(new ObjectMapper().writeValueAsString(result));
      return;
    }
 
    // 计算结果
    double resultValue = calculate(expression);
 
    // 返回计算结果
    result.put("result", resultValue);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.print(new ObjectMapper().writeValueAsString(result));
  }
 
  /**
   * 计算表达式
   *
   * @param expression 表达式
   * @return 计算结果
   */
  private double calculate(String expression) {
    // 解析表达式
    ExpressionParser parser = new ExpressionParser();
    Expression expressionTree = parser.parse(expression);
 
    // 计算表达式
    Calculator calculator = new Calculator();
    return calculator.calculate(expressionTree);
  }
}