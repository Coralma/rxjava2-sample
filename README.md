# rxjava2-sample

设计：
class ParallelMaster {
  
  public ParallelMaster(int poolSize, IParallelWorker... workers) {}
  
  public void execute(final Context context) {
    Rxjava -> zip(workers, new Function(new Function<Context, Boolean>() {
      @Override
      public Boolean apply(IParallelWorker worker) throws Exception {
        worker.execute(context);
      }
    }))
  }
}

通用的接口IParallelWorker
interface IParallelWorker {
  private boolean execute(Context context);
}

