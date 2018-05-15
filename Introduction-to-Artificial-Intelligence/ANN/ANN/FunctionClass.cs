using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ANN
{
    public delegate double GodFunction(List<double> Xz);
    public delegate double InvokeFunction(double v);
    public delegate double InvokeFunctionDerivative(double y);
    
    public class Function_Pair
    {
        public Function_Pair(InvokeFunction IF, InvokeFunctionDerivative IFD)
        {
            this.IF = IF;
            this.IFD = IFD;
            ;
        }
        public InvokeFunction IF
        {
            get;
            private set;
        }
        public InvokeFunctionDerivative IFD
        {
            get;
            private set;
        }
    }

    public static class Functions
    {
         public static double a = 1;
         public static Function_Pair FP0 = new Function_Pair(InvokeFunction_NULLF, InvokeFunction_NULLF_derivative);
         public static Function_Pair FP1 = new Function_Pair(InvokeFunction_fbs, InvokeFunction_fbs_derivative);
         public static Function_Pair FP2 = new Function_Pair(InvokeFunction_fhts, InvokeFunction_fhts_derivative);
         public static double InvokeFunction_NULLF(double v)
         {
             return v;
         }
         public static double InvokeFunction_NULLF_derivative(double y)
         {
             return 1;
         }
          public static double InvokeFunction_fbs(double v)
        {
            return  1.0 / (1.0 + Math.Pow(Math.E, (-1.0 * a * v)));
            
        }
          public static double InvokeFunction_fbs_derivative(double y)
        {
            double res = a * y * (1.0 - y);
            return res;
        }
          public static double InvokeFunction_fhts(double v)
          {
             return  (1.0 - Math.Pow(Math.E, (-2.0 * a * v))) / (1.0 + Math.Pow(Math.E, (-2.0 * a * v)));              
          }

          public static double InvokeFunction_fhts_derivative(double y)
          {
              double res = a * (1.0 + y) * (1.0 - y);
              return res;
          }
          public static double line3(List<double> Xs)
          {
              double y = Xs[0] * 1.0 + Xs[1] *(- 2.0) + Xs[2] * (-1.0);
              return y;
          }

          public static double line2(List<double> Xs)
          {
              double y = Xs[0] * 1.0 + Xs[1] * (1.0);
              return y;
          }

    }

    //public class FunctionClass
    //{
    //    public static double a = 1;
    //    /// <summary>
    //    /// 值域在0到1的s型函数 logistic函数
    //    /// </summary>
    //    /// <param name="Xs"></param>
    //    /// <returns></returns>
    //    public static double InvokeFunction_fbs(List<double> Xs)
    //    {
    //        double v = 1.0 / (1.0 + Math.Pow(Math.E, (-1.0 * a * Xs[0])));
    //        return v;
    //    }

    //    /// <summary>
    //    /// 双曲正切函数。值域在-1和1之间。
    //    /// </summary>
    //    /// <param name="Xs"></param>
    //    /// <returns></returns>
    //    public static double InvokeFunction_fhts(List<double> Xs)
    //    {
    //        double v = (1.0 - Math.Pow(Math.E, (-2.0 * a * Xs[0]))) / (1.0 + Math.Pow(Math.E, (-2.0 * a * Xs[0])));
    //        return v;
    //    }


    //   /// <summary>
    //   /// 空函数，即不加函数的情形。
    //   /// </summary>
    //   /// <param name="Xs"></param>
    //   /// <returns></returns>
    //   public static double InvokeFunction_NULLF(List<double >Xs)
    //    {
    //        return Xs[0];
    //    }


    //   /// <summary>
    //   ///  fbs 的导数，可以表示为函数本身。
    //   /// </summary>
    //   /// <param name="y"></param>
    //   /// <returns></returns>
    //    public static double InvokeFunction_fbs_derivative(double y)
    //    {
    //        double res = a * y * (1.0 - y);
    //        return res;
    //    }

    //   /// <summary>
    //   /// fhts的导数，也可以表示为函数本身。
    //   /// </summary>
    //   /// <param name="y"></param>
    //   /// <returns></returns>
    //    public static double InvokeFunction_fhts_derivative(double y)
    //    {
    //        double res = a * (1.0 + y)*(1.0 - y);
    //        return res;
    //    }


    //   /// <summary>
    //   /// 导数为1.
    //   /// </summary>
    //   /// <param name="y"></param>
    //   /// <returns></returns>
    //   public static double InvokeFunction_NULLF_derivative(double y)
    //    {
    //        return 1;
    //    }

    //   /// <summary>
    //   /// 一个线性模型
    //   /// </summary>
    //   /// <param name="Xs"></param>
    //   /// <returns></returns>
    //   public static  double line3(List<double> Xs)
    //    {
    //        double y = Xs[0] * 1 + Xs[1] * 0.5 + Xs[2] * (-1);
    //        return y;
    //    }
    //}
}
