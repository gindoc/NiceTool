package com.a3xh1.basecore.utils.gson;

import com.a3xh1.basecore.pojo.response.ErrorResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import timber.log.Timber;

public final class GsonDConverterFactory extends Converter.Factory {

    public static GsonDConverterFactory create() {
        return create(new Gson());
    }

    public static GsonDConverterFactory create(Gson gson) {
        return new GsonDConverterFactory(gson);
    }

    private final Gson gson;

    private GsonDConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody,
            ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new GsonResponseBodyConverter<>(gson, type);
    }

    @Override
    public Converter<?,
            RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

    final class GsonResponseBodyConverter<T> implements Converter<ResponseBody,
            T> {
        private final Gson gson;
        private final Type type;

        GsonResponseBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
        }

        /**
         * 针对数据返回成功、错误不同类型字段处理
         */
        @Override
        public T convert(ResponseBody value) throws IOException {
            String response = value.string();
            try {
                // 这里的type实际类型是 HttpResult<PhoneBean>  PhoneBean就是retData要获取的对象。
                return gson.fromJson(response, type);
            } catch (JsonSyntaxException exception) {
                Timber.d("返回err==" + response);
                ErrorResponse errorResponse = gson.fromJson(response, ErrorResponse.class);
                throw new ResultException(errorResponse.isStatus(), errorResponse.getDesc());
            } finally {
                value.close();
            }
        }
    }
}