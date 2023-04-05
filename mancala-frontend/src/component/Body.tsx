import React, {ReactNode} from 'react';

type Props = {
    children: ReactNode
}

const Body: React.FC<Props> = ({children}) => {
    return (
        <div className="my-5 container">
            {children}
        </div>
    );
};

export default Body;
