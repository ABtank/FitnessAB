package ru.abtank.fitnessab.persist.repositories;


import ru.abtank.fitnessab.persist.entities.Type;

import java.util.LinkedList;
import java.util.List;

public class UnitOfWork {
    //Используем ThreadLocal , то поможет инициировать новый объект UnitOfWork в рамках текущего потока
    private static ThreadLocal<UnitOfWork> current = new ThreadLocal<>();

    private IdentityMap identityMap;

    private TypeMapper repository;

    private List<Type> newTypes = new LinkedList<>();
    private List<Type> dirtyTypes = new LinkedList<>();
    private List<Type> removedTypes = new LinkedList<>();

    public UnitOfWork(IdentityMap identityMap) {
        this.identityMap = identityMap;
    }

    /**
     * процесс инициализации транзакции
     * создаются новые объекты {@link UnitOfWork} и {@link IdentityMap},
     * т.к. сам {@link UnitOfWork} нуждается в {@link IdentityMap} при регистрации новых и удалении старых сущностей
     */
    public static void init(){
        IdentityMap.init();
        setCurrent(new UnitOfWork(IdentityMap.getCurrent()));
    }

    /**
     * устанавливает текущий {@link UnitOfWork} в локальный(текущий) поток
     * @param unitOfWork
     */
    private static void setCurrent(UnitOfWork unitOfWork){
        current.set(unitOfWork);
    }

    /**
     * возвращает текущий объект {@link UnitOfWork}
     * альтернатива использованию {@link Registry}
     * @return
     */
    public static UnitOfWork getCurrent(){
        return current.get();
    }

    /**
     * При создании новой сущности {@link Type} добавим ее в кэш созданных новых
     * @param entity новая созданная сущность
     */
    public void registerNew(Type entity){
        identityMap.add(entity);
        if(!newTypes.contains(entity)){
            newTypes.add(entity);
        }
    }

    /**
     * По аналогии регистрируем измененные сущности
     * @param type
     */
    public void registerDirty(Type type){
        if(newTypes.contains(type)){
            throw new IllegalArgumentException();
        }

        dirtyTypes.add(type);
    }

    /**
     * регистрация удаленных из бд сущностей
     * @param order
     */
    public void registerRemoved(Type order){
        newTypes.remove(order);
        dirtyTypes.remove(order);
        removedTypes.add(order);
    }

    /**
     * в самом конце обработки запроса фиксируем изменения в бд( проливаем сущности из коллекций в бд единым скриптом)
     */
    public void commit(){
        insertNew();
        update();
        delete();
    }

    private void delete() {

    }

    private void update() {

    }

    /**
     * сохраняем все сущности из newTypes
     */
    private void insertNew() {
        repository.insert(newTypes);
    }
}
