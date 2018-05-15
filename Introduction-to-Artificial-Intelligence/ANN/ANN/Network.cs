using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ANN
{
  
    public  class Network
    {
        /// <summary>
        /// 网络中的所有层
        /// </summary>
        public List<Layer> Layers
        {
            get;
            private set;
        }
        /// <summary>
        /// 有多少层，最少两层。
        /// </summary>
        public int N_Layers
        {
            get
            {
                return Layers.Count;
            }
        }

        /// <summary>
        /// 是否有偏置，如果有，所有的层都有，所有的节点也都有。
        /// </summary>
        public bool withBias
        {
            get;
            private set;
        }
        
        /// <summary>
        /// ns 记录从输入层开始，每层的节点个数。
        /// withBias 为真，表示所有的节点均带有偏置。
        /// fp 激活函数及其导数
        /// u  训练步长
        /// ranndomOrNull 初始化权值的方法
        /// </summary>  
        public Network (List<int> ns,bool withBias,Function_Pair fp,double u,bool RandomOrNull)
        {
            Layers = new List<Layer>();
            this.withBias = withBias;
            for (int i = 0; i < ns.Count;i++ )
            {
                int n = ns[i];
                bool IsInput=false;
                bool IsOutput = false;
                if (i == 0) 
                    IsInput = true;
                if (i == ns.Count - 1)
                {
                    IsOutput = true;
                    withBias = false;//输出层不会有偏置。
                }
                Layer layer = new Layer(withBias ,n, IsInput,IsOutput,this,i,fp);
                Layers.Add(layer);
            }
            this.u = u;
            GodFunctions = new List<GodFunction>();
            InitializeWeight(RandomOrNull);
        }  
     
        /// <summary>
        /// 取出第idx层。
        /// </summary>
        /// <param name="idx"></param>
        /// <returns></returns>
        public  Layer this[int idx]
        {
            get
            {
                return Layers[idx];
            }
        }

        /// <summary>
        /// 取出输出层
        /// </summary>
        public Layer OutputLayer
        {
            get
            { 
                return this[this.N_Layers - 1];
            }
        }


        /// <summary>
        /// 设置网络输入，不包含偏置（不论有偏置与否）
        /// </summary>
        /// <param name="inputValues"></param>
        public void SetInputs(List<double> inputValues)
        {
            int N_inputs = this[0].N_nodes;
            int n = inputValues.Count;
            if (withBias) n++;
            if (N_inputs != n) 
                throw (new Exception("wrong input value numbers"));
            this[0].SetInputs(inputValues);
        }

        /// <summary>
        /// 得到期望输出，有的期望输出可以以解析的方式求解。
        /// 有的需要以区域划分的方式给出，有的只能以手工的方式给出。
        /// 有几个输出节点，就有几个输出值。
        /// </summary>
        /// <param name="Dests"></param>
        public List<double> GetDest()
        {
            List<double> dests = new List<double>();
            foreach (GodFunction f in this.GodFunctions)
            {
                List<double> Xs = GetInputs();
                double d = f(Xs);
                dests.Add(d);
            }
            return dests;
        }

       
        /// <summary>
        /// 实际的输入与输出之间的关系函数。
        /// 有几个输出节点就有几个函数。
        /// </summary>
        public List<GodFunction> GodFunctions
        {
            get;
            set;
        }


        /// <summary>
        /// 取出网络输入，不包括偏置。
        /// </summary>
        /// <returns></returns>
        public List<double> GetInputs()
        {
           return  this[0].GetOutputs();           
        }

        /// <summary>
        /// 正向计算整个网络。
        /// </summary>
        /// <param name="outPuts"></param>
       public void Compute()
        {         
           foreach (Layer layer in Layers)
           {
                if (layer.IsInput) 
                   continue;
                layer.Compute();              
           }
           GetError();
        }

        /// <summary>
        /// 输出层的输出
        /// </summary>
        /// <returns></returns>
       public List<double> GetOutput()
       {
          return  OutputLayer.GetOutputs();
       }

        /// <summary>
        /// RandomOrNull 为真则随机赋值否则赋值为0；
        /// </summary>
        /// <param name="RandomOrNull"></param>
        public void InitializeWeight(bool RandomOrNull)
       {
            foreach (Layer layer in Layers)
            {
                if(!layer .IsInput )
                {
                    foreach (Node node in layer.nodes)
                        node.InitializeWeight(RandomOrNull);
                }
            }
       }


        /// <summary>
        /// 当前输入的误差平方和。
        /// </summary>
        public double Error
        {
            get;
            private set;
        }
        /// <summary>
        /// 对网络进行一次训练
        /// InputValues为输入数据。
        /// DestValues为期望输出。
        /// 如返回真，则终止训练。
        /// </summary>
        /// <param name="inputValues"></param>
        /// <param name="outputValues"></param>
        /// <returns></returns>
        public bool Train(List<double> inputValues,double alpha)
        {
            this.SetInputs(inputValues);
            this.Compute();//Error= GetError();
            AdjustWeights();
            if (Error > alpha)
                return false;
            else
                return true;
        }

        /// <summary>
        /// 训练步长。
        /// </summary>
        public double u
        {
            get;
            set;
        }

        public void AdjustWeights()
        {
            for(int i=Layers.Count-1 ;i>-1;i--)
            {
                Layer layer = Layers[i];
                layer.adjustWeights();
            }
        }

   
/// <summary>
/// 误差平方和
/// </summary>
/// <returns></returns>
       public double GetError()
        {           
                List<double> output = this.GetOutput();
                List<double> dest = this.GetDest();
                double sum = 0;
                for (int i = 0; i < output.Count; i++)
                {
                    sum += Math.Pow((output[i] - dest[i]), 2);
                }
                sum /= 2;
                Error = sum;
                return sum;           
        }
    }
}
