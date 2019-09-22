import * as React from "react";
import './item-details.scss';

export default class ItemDetails extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentItem: this.props.item ? {...this.props.item} : {}
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.item !== this.props.item) {
            this.setState({currentItem: {...this.props.item}})
        }
    }

    updateField(fieldName) {
        return (e) => {
            const val = e.target.value;
            this.setState({
                currentItem: {
                    ...this.state.currentItem,
                    [fieldName]: val
                }
            });
        }
    }

    save() {
    }

    render() {
        const {name, category} = this.props.item;

        return (
            <div className="details">
                <div className="actions">
                    <button>Save</button>
                    <button>Delete</button>
                </div>
                <div className="fields">
                    <div>
                        <label htmlFor='name'>Name:</label><input id='name' onChange={this.updateField('name').bind(this)}
                                                                  value={name}/>
                    </div>
                    <div>
                        <label htmlFor='category'>Category:</label><input id='name'
                                                                          onChange={this.updateField('name').bind(this)}
                                                                          value={category}/>
                    </div>
                </div>
            </div>
        )
    }
}