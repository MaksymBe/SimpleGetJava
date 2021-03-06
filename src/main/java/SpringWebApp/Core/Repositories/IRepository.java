package SpringWebApp.Core.Repositories;

import Framework.RepositoryException;

public interface IRepository<T> {
    T[] getAll() throws RepositoryException;

    T getById(Integer id) throws RepositoryException;

    T delete(Integer id) throws RepositoryException;

    T create(T object) throws RepositoryException;

    T update(Integer id, T object) throws RepositoryException;
}
