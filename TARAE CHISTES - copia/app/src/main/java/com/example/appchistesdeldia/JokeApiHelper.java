package com.example.appchistesdeldia;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/**
 * Clase utilitaria para obtener chistes desde JokeAPI usando OkHttp.
 */
public class JokeApiHelper {
    // URL de la API con parámetros para obtener un chiste seguro, en español, tipo single
    private static final String API_URL = "https://v2.jokeapi.dev/joke/Any?type=single&lang=es&amount=1&safe-mode";

    // User-Agent válido para evitar errores 403
    private static final String USER_AGENT = "Mozilla/5.0 (Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36";

    // Interfaz de callback para retornar el resultado de la petición
    public interface JokeCallback {
        void onJokeReceived(String joke);
        void onError(String errorMsg);
    }

    /**
     * Realiza una petición GET a la API de chistes y retorna el resultado por callback.
     * @param callback Callback para manejar el resultado o error.
     */
    public static void getJoke(final JokeCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // Construir la petición con User-Agent personalizado
        Request request = new Request.Builder()
                .url(API_URL)
                .header("User-Agent", USER_AGENT)
                .build();

        // Ejecutar la petición de forma asíncrona
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Error de red o conexión
                postError(callback, "Error de conexión: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    if (!response.isSuccessful()) {
                        // Manejo de errores HTTP
                        int code = response.code();
                        if (code == 403) {
                            postError(callback, "Error 403: Acceso prohibido. User-Agent inválido o bloqueado.");
                        } else if (code == 429) {
                            postError(callback, "Error 429: Límite de peticiones alcanzado. Espera un minuto e inténtalo de nuevo.");
                        } else {
                            postError(callback, "Error HTTP " + code + ": " + response.message());
                        }
                        response.close();
                        return;
                    }
                    // Manejo seguro del body
                    if (response.body() == null) {
                        postError(callback, "Respuesta vacía de la API.");
                        response.close();
                        return;
                    }
                    String body = response.body().string();
                    response.close();
                    try {
                        JSONObject json = new JSONObject(body);
                        // Extraer el chiste del campo "joke"
                        if (json.has("joke")) {
                            String joke = json.getString("joke");
                            postJoke(callback, joke);
                        } else {
                            postError(callback, "Respuesta inesperada de la API.");
                        }
                    } catch (JSONException e) {
                        postError(callback, "Error al parsear el chiste: " + e.getMessage());
                    }
                } catch (Exception e) {
                    postError(callback, "Error inesperado: " + e.getMessage());
                }
            }
        });
    }

    // Métodos auxiliares para asegurar que el callback se ejecute en el hilo principal (UI)
    private static void postJoke(final JokeCallback callback, final String joke) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                callback.onJokeReceived(joke);
            }
        });
    }

    private static void postError(final JokeCallback callback, final String errorMsg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorMsg);
            }
        });
    }
}
