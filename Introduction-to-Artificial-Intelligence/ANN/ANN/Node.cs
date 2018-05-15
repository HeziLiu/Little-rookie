using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ANN
{
   
   public  class Node
    {
       public Node(bool IsBias,Layer layer,Function_Pair fp)
       {
           this.IsBias = IsBias;        
           this.layer = layer;
           if (!this.IsInput)
           {
               this.FP = fp; // 输入层无需激活函数。
               Inputs = new List<Node>();
               weights = new List<double>();
           }
       }

       public bool IsBias
       {
           get;
           set;
       }
      public  List<Node> Inputs
       {
           get;
           set;
       }

       /// <summary>
       /// 未知权重
       /// </summary>
      public List<double> weights
      {
          get;
          set;
      }

       Function_Pair FP
       {
           get;
           set;
       }
      
       public bool IsInput
       {
           get
           {
               return layer.IsInput;
           }
       }

       public bool IsOutput
       {
           get
           {
               return layer.IsOutput;
           } 
       }

       public double output;
       /// <summary>
       /// 实际输出
       /// </summary>
       //public double output
       //{
       //    get
       //    {
       //        if(IsInput )
       //            return outv;
       //        double sum=0;
       //        for (int i = 0; i < Inputs.Count; i++)
       //            sum += Inputs[i].output  * weights[i];
       //        if (this.FP  == null) return sum;               
       //        return FP.IF (sum);
       //    }
       //    set
       //    {
       //        if (!IsInput) 
       //            throw new Exception("only for node in input layer ");
       //        outv=value ;
       //    }           
       //}



       public double GetOutput()
       {
           if (IsInput)
               return this.output;
           this.output = 0;
           for (int i = 0; i < Inputs.Count; i++)
               output += Inputs[i].output * weights[i];
           if (this.FP != null)
               output = FP.IF(output);
           return output;
       }
       public double Compute()
       {
           return GetOutput ();
       }

       /// <summary>
       /// 误差
       /// </summary>
       public double error
       {
           get;
           set;
       }
       public Layer layer
       {
           get;
           set;
       }

       public void InitializeWeight(bool RandomOrNull)
       {
           Random r = new Random(DateTime.Now.Millisecond);
           for (int i = 0; i < weights.Count;i++ )
           {
               if (RandomOrNull)
                   weights[i] = r.NextDouble();
               else
                   weights[i] = 0;
           }
       }

       public Network net
       {
           get
           {
               return layer.net;
           }
       }

       /// <summary>
       /// 在该层中属于第几个节点。
       /// </summary>
       public int Nth_nodes
       {
           get;
           set;
       }
       public GodFunction GodFunction
       {
           get
           {
               return net.GodFunctions[Nth_nodes];
              
           }
       }

       public double dest
       {
           get
           {
               return this.GodFunction(net.GetInputs());
           }
       }

       public double delta;
        ///<summary>
        ///如果是输出节点的话：
        ///</summary>
       public double Delta
       {
           get
           {
               if (this.IsOutput)
               {
                   double y = this.output;
                   double e = (this.dest - y);                 
                   double fiderive = FP.IFD(y);
                   delta= e * fiderive;
                   return delta;
               }
               else
               {
                   Layer nextLayer = this.NextLayer;
                   double sum=0;
                   foreach (Node nextnode in nextLayer.nodes  )
                   {
                       sum += nextnode.delta * nextnode.weights[this.Nth_nodes];
                   }
                   double y = this.output;
                   double fiderive = FP.IFD(y);
                   delta = sum * fiderive;
                   return delta;
               }
           }
       }

       Layer NextLayer
       {
           get
           {
               return layer.NextLayer;
           }
       }

       /// <summary>
       /// 步长。
       /// </summary>
       public double u
       {
           get
           {
               return net.u;
           }
       }

       public void adjustWeights()
       {
           if (this.IsInput) return;
           for (int i = 0; i < Inputs.Count;i++ )
           {
               Node node = Inputs[i];
               double yi = node.output;               
               double dw = deltaWji(i);
               this.weights[i] += dw;
           }
       }

       /// <summary>
       /// 第i个输入节点对应的权重的改变值。
       /// </summary>
       /// <param name="i"></param>
       /// <returns></returns>
       public double deltaWji(int i)
       {
           double res = u * Delta * Inputs[i].output;
           return res;
       }


       public override string ToString()
       {   string s="w: ";
           if (this.IsInput) 
               return "output is "+this.output.ToString ();           
           else
           {
               foreach (double w in weights )
               {
                   s += " " + w.ToString();
               }
           }
           return s;
       }

     
       
    }
}
