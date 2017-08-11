package com.coral.rxjava.modelCombine;

import com.coral.rxjava.modelCombine.model.Customer;
import com.coral.rxjava.modelCombine.model.Insured;
import com.coral.rxjava.modelCombine.model.Product;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.util.List;

/**
 * Created by ccc on 2017/8/10.
 */
public class ProductCombineMain {

    private Gson gson = new Gson();
    private int insuredId, customerId, mobileId = 1;

    public static void main(String[] args) {
        ProductCombineMain main = new ProductCombineMain();
        main.execute();
    }

    public void execute() {
        final List<Product> products = loadProducts();
        Observable<Product> productObservable = Observable.create(new ObservableOnSubscribe<Product>() {
            @Override
            public void subscribe(ObservableEmitter<Product> e) throws Exception {
                for(Product p : products) {
                    e.onNext(p);
                }
                e.onComplete();
            }
        });
        productObservable.subscribe(insuredConsumer());
        productObservable.subscribe(customerConsumer());
        productObservable.subscribe(printConsumer());
    }

    protected Consumer printConsumer() {
        return new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(gson.toJson(o));
            }
        };
    }

    protected  Consumer<Product> insuredConsumer() {
        return new Consumer<Product>() {
            @Override
            public void accept(Product p) throws Exception {
                p.setInsureds(new Insured("insured" + insuredId++));
            }
        };
    }

    protected  Consumer<Product> customerConsumer() {
        return new Consumer<Product>() {
            @Override
            public void accept(Product p) throws Exception {
                p.setCustomers(new Customer("customer" + customerId++, "mobile" + mobileId++));
            }
        };
    }

    private List<Product> loadProducts() {
        List<Product> products = Lists.newArrayList();
        products.add(new Product(1000l));
        products.add(new Product(2000l));
        products.add(new Product(3000l));
        return products;
    }
}
