/**
 * Service Locator
 *
 * @see https://medium.com/@avinkavish/decoupling-your-typescript-modules-with-the-service-locator-pattern-1e9d6e8378ce
 */
export class ServiceLocator {

    static readonly Instance = new ServiceLocator();

    private _map = new Map<string, any>();

    private constructor() {}

    get<T>(key: string): T | undefined {
        return this._map.get(key);
    }

    add(key: string, instance: {}): ServiceLocator {
        this._map.set(key, instance);
        return this;
    }

    clear(): void {
        this._map.clear();
    }

    static get<T>(key: string): T | undefined {
        return this.Instance.get(key);
    }

    static add(key: string, instance: {}): ServiceLocator {
        return this.Instance.add(key, instance);
    }

    static clear(): void {
        this.Instance.clear();
    }

}
