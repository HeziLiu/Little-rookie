using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ANN
{
    public class Layer
    {
        public List<Node> nodes
        {
            get;
            set;
        }
        public Network net
        {
            get;
            set;
        }
        /// <summary>
        /// 层数，输入层为第0层。
        /// </summary>
        public int Nth_layer
        {
            get;
            set;
        }

        /// <summary>
        /// 节点个数，包含偏置节点（如果有的话）
        /// </summary>
        public int N_nodes
        {
            get
            { return nodes.Count; }
        }
        /// <summary>
        /// 是否带偏置，该层的节点个数（不包含偏置在内）
        /// 是否为输入层，是否为输出层。
        /// 网络，该层的层数。激活函数。
        /// </summary>
        public Layer(bool withBias,int N_nodes,bool IsInput,bool IsOutput,Network net,int Nth_layer,Function_Pair fp)
        {
            nodes = new List<Node>();
            this.IsInput = IsInput;
            this.IsOutput = IsOutput;
            this.net = net;
            this.Nth_layer = Nth_layer;
            if (withBias)
                 nodes.Add(new Node(true, this,fp));
            for(int i=0;i<N_nodes ;i++)
            {
                Node node = new Node(false, this,fp);
                nodes.Add(node);
                if(!node.IsInput)
                {
                    foreach (Node pnode in this.PreviousLayer.nodes)
                    {
                        node.Inputs.Add(pnode);
                        node.weights .Add (new double());
                    }
                }                
            }
        }

        public bool IsInput
        {
            get;
            set;
        }

        public bool IsOutput
        {
            get;
            set;
        }

        public Layer PreviousLayer
        {
            get
            {
                if (Nth_layer == 0) return null;
                return net.Layers[Nth_layer - 1];
            }
        }

        public Layer NextLayer
        {
            get
            {
                if (Nth_layer == net.N_Layers - 1) return null;
                return net.Layers[Nth_layer + 1];
            }
        }

        /// <summary>
        /// 只对输入层有意义,偏置节点设为1.
        /// </summary>
        /// <param name="v"></param>
        public void SetInputs(List<double >v)
        {   
            if(!this.IsInput ) 
                throw new Exception ("only for input layer");
            int i=0;
            foreach (Node node in nodes)
            {
                if (node.IsBias)
                    node.output = 1;
                else
                    node.output = v[i];
                i++;
            }
        }

        /// <summary>
        /// 对于输入层就是其输入，对于输出层，是网络最终的输出。偏置节点不用取。
        /// </summary>
        /// <returns></returns>
        public List<double >  GetOutputs()
        {           
            List<double> vs = new List<double>();
            foreach (Node node in nodes)
            {
                if (node.IsBias) 
                    continue;               
                else
                    vs.Add(node.output);               
            }
            return vs;
        }

        public void Compute()
        {
            foreach (Node node in this.nodes )
            {
                node.Compute ();
            }
        }

       public void adjustWeights()
        {
            if (this.IsInput)
                return;
            foreach (Node node in this.nodes)
                node.adjustWeights();
        }

       

    }
}
