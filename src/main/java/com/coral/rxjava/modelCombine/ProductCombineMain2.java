package com.coral.rxjava.modelCombine;

import com.coral.rxjava.modelCombine.model.Customer;
import com.coral.rxjava.modelCombine.model.Insured;
import com.coral.rxjava.modelCombine.model.Product;
import com.coral.rxjava.modelCombine.model.Report;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ccc on 2017/8/10.
 */
public class ProductCombineMain2 {

    private Gson gson = new Gson();
    private int num = 30;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws InterruptedException {
        ProductCombineMain2 main = new ProductCombineMain2();
        main.execute();
    }

    public void execute() throws InterruptedException {
        final List<Product> products = loadProducts();
        final List<Report> reportIterator = ruleExecute(products);
        /*Observable.fromIterable(reportIterator).observeOn(Schedulers.from(fixedThreadPool)).forEach(new Consumer<Report>() {
            @Override
            public void accept(Report report) throws Exception {
                printThread("forEach");
            }
        });*/
        Stopwatch sw = Stopwatch.createStarted();
        Flowable<Report> flowable = Flowable.fromIterable(reportIterator);
        flowable.parallel(reportIterator.size())
                .runOn(Schedulers.from(fixedThreadPool))
                .sequential()
                .forEach(new Consumer<Report>() {
                    @Override
                    public void accept(Report s) throws Exception {
                        s.setAuditData("Audit-" + s.getId());
                        System.out.println("Flowable forEach, thread is " + Thread.currentThread().getName());
                    }
                });
        System.out.println(sw.stop());
        //Thread.sleep(Long.MAX_VALUE);
    }

    public List<Report> ruleExecute(final List<Product> products) {
        Observable<List<Report>> reportObservable = Observable.create(new ObservableOnSubscribe<List<Product>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Product>> e) throws Exception {
                printThread("Product data loading");
                e.onNext(productDataLoading(products));
            }
        }).map(new Function<List<Product>, List<Report>>() {
            @Override
            public List<Report> apply(List<Product> products) throws Exception {
                printThread("Product convert to Report");
                List<Report> reports = convertToReport(products);
                return reports;
            }
        });
        List<Report> resultProducts = reportObservable.blockingFirst();
        return resultProducts;
    }

    public List<Product> productDataLoading(List<Product> products) {
        for(Product p : products) {
            p.setInsureds(new Insured("insured-" + p.getId()));
            p.setCustomers(new Customer("customer-" + p.getId(), "mobile-" + p.getId()));
        }
        return products;
    }

    public List<Report> convertToReport(List<Product> products) {
        List<Report> reports = Lists.newArrayList();
        for(Product product : products) {
            Report report = new Report(product);
            report.setReportData("reportData-" + product.getId());
            report.setExtData("extData-" + product.getId());
            reports.add(report);
        }
        return reports;
    }

    protected Consumer printConsumer() {
        return new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("printConsumer, thread is " + Thread.currentThread().getName());
                System.out.println(gson.toJson(o));
            }
        };
    }

    protected  Consumer<Product> insuredConsumer() {
        return new Consumer<Product>() {
            @Override
            public void accept(Product p) throws Exception {
                System.out.println("insuredConsumer, thread is " + Thread.currentThread().getName());
                p.setInsureds(new Insured("insured" + p.getId()));
            }
        };
    }

    protected  Consumer<Product> customerConsumer() {
        return new Consumer<Product>() {
            @Override
            public void accept(Product p) throws Exception {
                System.out.println("customerConsumer, thread is " + Thread.currentThread().getName());
                p.setCustomers(new Customer("customer" + p.getId(), "mobile" + p.getId()));
            }
        };
    }

    private List<Product> loadProducts() {
        List<Product> products = Lists.newArrayList();
        for(int i=1;i <= num;i++) {
            long id = i*1000;
            products.add(new Product(id));
        }
        return products;
    }

    private void printThread(String methodName) {
        System.out.println(methodName + ", thread is " + Thread.currentThread().getName());
    }
}
