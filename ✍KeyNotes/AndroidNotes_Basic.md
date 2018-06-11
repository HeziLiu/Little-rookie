## Activity的生命周期

![img](http://img.my.csdn.net/uploads/201303/08/1362732913_3457.jpg) 

## 介绍不同场景下Activity生命周期的变化过程

+ **启动Activity：**onCreate()->onStart()->onResume(),Activity进入运行状态
+ **Activity退居后台：**当前Activity转到新的Activity界面或按Home键回到主屏：onPause()->onStop(),进入停滞状态。
+ **Activity返回前台：**onRestart()->onStart()->onResume()，再次回到运行状态
+ **Activity退居后台**，**且系统内存不足**，系统会杀死这个后台状态的Activity，若再次回到这个Activity，则会走onCreate()->onStart()->onResume()
+ **锁定屏与解锁屏幕** 只会调用onPause(),而不会调用onStop()方法，开屏后则调用onResume()



## Activity销毁但Task如果没有销毁掉，当Activity重启时这个AsyncTask该如何解决？

屏幕旋转，在重建Activity的时候，会回调``Activity.onRetainNonConfigurationInstance()``重新传递一个新的对象给AsyncTask，完成引用的更新

## Asynctask为什么要设置为只能够一次任务

考虑到线程安全问题

## 若Activity已经销毁，此时AsynTask执行完并返回结果，会报异常么？

当一个App旋转时，整个Activity会被销毁和重建。当Activity重启时，AsyncTask中对该Activity的引用是无效的，因此``onPostExecute()``就会不起作用，若AsynTask正在执行，则会报view not attached to window manager异常

同样也是生命周期的问题，在Activity的onDestory()方法中调用Asyntask.cancel方法，让二者的声明周期同步

## 内存不足时，系统会杀死后台的Activity，如果需要进行一些临时状态的保存，在哪个方法进行

Activity的``onSaveInstanceState()``和``onRestoreInstanceState()``并不是生命周期方法，不同于``onCreate()``、``onPause()``等生命周期方法，它们并不一定会被触发。当应用遇到意外情况（内存不足，用户直接按Home键）由系统销毁一个Activity，``onSaveInstanceState()``会被调用。但是当用户主动去销毁一个Activity时，例如在应用中按返回键，``onSaveInstanceState()``就不会被调用。除非该activity是被用户主动销毁的，通常``onSaveInstanceState()``只适合用于保存一些临时性的状态，而``onPause()``适合用于数据的持久化保存

## Activity 四种launchMode

+ standard
+ singleTop

