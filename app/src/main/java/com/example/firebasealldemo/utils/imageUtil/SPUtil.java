package com.example.firebasealldemo.utils.imageUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.firebasealldemo.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


public class SPUtil {


    private static SharedPreferences getSPFile(Context context){
        return context.getSharedPreferences(Constants.sp,
                Context.MODE_PRIVATE);
    }
    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = getSPFile(context);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key,  object==null?"":(String)object);
        } else if (object instanceof Integer) {
            editor.putInt(key, object==null?0:(Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, object==null?false:(Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, object==null?0:(Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, object==null?0:(Long) object);
        } else {
            editor.putString(key, object==null?"":object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }



    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = getSPFile(context);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }
    public static Object get(String key, Object defaultObject) {
        return get(MainApplication.getAppContext(),key,defaultObject);
    }
    public static String getString(String key, String defaultObject) {
        return (String) get(key,defaultObject);
    }
    public static boolean getBoolean(String key, boolean defaultObject) {
        return (boolean) get(key,defaultObject);
    }
    public static int getInt(String key, int defaultObject) {
        return (int) get(key,defaultObject);
    }
    public static float getFloat(String key, float defaultObject) {
        return (float) get(key,defaultObject);
    }
    public static long getLong(String key, long defaultObject) {
        return (long) get(key,defaultObject);
    }
    public static double getDouble(String key, double defaultObject) {
        return (double) get(key,defaultObject);
    }
    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp =getSPFile(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }
    public static void remove(String key) {
        remove(MainApplication.getAppContext(),key);
    }
    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp =getSPFile(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }
    public static void clear() {
        clear(MainApplication.getAppContext());
    }
    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = getSPFile(context);
        return sp.contains(key);
    }
    public static boolean contains(String key) {
        return contains(MainApplication.getAppContext(),key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp =getSPFile(context);
        return sp.getAll();
    }
    public static Map<String, ?> getAll() {
        return getAll(MainApplication.getAppContext());
    }
    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }



}