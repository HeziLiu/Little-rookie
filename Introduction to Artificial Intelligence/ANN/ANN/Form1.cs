using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Threading;

namespace ANN
{
    public partial class Form1 : Form
    {
        Network net;
        double u = 0.2;  // 步长
        int nInput = 2;   // 输入个数
        int nOutput = 1;  // 输出个数
        double alpha = 0.00000000001;  // 收敛阀门

        public Form1()
        {
            InitializeComponent();
            initialize();
        }
        bool randomInput = false ; 
        private void button1_Click(object sender, EventArgs e)
        {
            randomInput = true; 
            Thread th = new Thread(new ThreadStart(ann));            
            th.Start(); 
        }
        void ann()
        {
            listView1.Items.Clear();        
            Random r=new Random (DateTime .Now .Millisecond ); 
            List<double> inputVs = new List<double>();
            int times = 0;
            while (times < 1000)
            {
                inputVs.Clear();
                if(randomInput)
                for (int i = 0; i < nInput; i++)
                {
                    inputVs.Add(r.Next(5));
                } 
                else
                {
                    inputVs.Add(di);
                    inputVs.Add(di+1);
                    di++;
                    if (di == 3) di = 0;
                }
                bool flag= net.Train(inputVs, alpha);
                ShowNet();
                if (flag) break;
                times++;
            }
            MessageBox.Show(times.ToString());
        }

       
       void ShowNet()
        {
            try
            {
                List<double> weights = net.Layers[1].nodes[0].weights;
                ListViewItem item = new ListViewItem();
                item.SubItems.Add(weights[0].ToString());
                item.SubItems.Add(weights[1].ToString());
                item.SubItems.Add(net.GetOutput()[0].ToString());
                item.SubItems.Add(net.GetDest()[0].ToString());
                item.SubItems.Add(net.Error.ToString());
                listView1.Items.Add(item);
            }
           catch
            {

            }
        }

       private void Initializebutton2_Click(object sender, EventArgs e)
       {
           initialize();
       }
   
        void initialize()
       {                     
           List<int> ns = new List<int>();
           ns.Add(nInput);
           ns.Add(nOutput);
           net = new Network(ns, false, Functions.FP0, u,false );
           net.GodFunctions.Add(Functions.line2);           
       }

        int di = 1;
        private void Stepbutton3_Click(object sender, EventArgs e)
        {
            List<double> inputVs = new List<double>();
            inputVs.Clear();
            inputVs.Add(di);
            inputVs.Add(di+1);
            //inputVs.Add(1);
            bool flag = net.Train(inputVs, alpha);
            ShowNet();
            di++;
            if (di == 3) di = 0;
        }
      
     
    }
}
